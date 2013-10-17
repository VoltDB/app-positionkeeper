package procedures;

import org.voltdb.*;
import org.voltdb.client.ClientResponse;

public class OrderInsert extends VoltProcedure {

    public final SQLStmt insertOrder = new SQLStmt(
        "INSERT INTO ord (codord, ord_cnt, ord_sec, ord_qty, ord_prc) VALUES (?, ?, ?, ?, ?);");

    public final SQLStmt updatePos = new SQLStmt(
        "UPDATE pos SET " +
        "  pos_cum_qty_ord = pos_cum_qty_ord + ?," +
        "  pos_cum_val_ord = pos_cum_qty_ord * pos_prc" +
        " WHERE codsec = ? AND codcnt = ?;");

    public final SQLStmt insertPos = new SQLStmt(
        "INSERT INTO pos VALUES (" +
        "?,?,?,?,?,?,?" +
        ");");

    public long run( int     codord,
                     int     ord_cnt,
                     int     ord_sec,
                     int     ord_qty,
                     double  ord_prc
		     ) throws VoltAbortException {


        voltQueueSQL(insertOrder, 
                     codord, 
                     ord_cnt,
                     ord_sec,
                     ord_qty,
                     ord_prc);
        
        voltQueueSQL(updatePos,
                     ord_qty,
                     ord_sec,
                     ord_cnt);

        VoltTable results1[] = voltExecuteSQL();
        
        long rowsAffected = results1[1].asScalarLong();

        if (rowsAffected == 0) {
            // then insert
            voltQueueSQL(insertPos,
                         ord_cnt,
                         ord_sec,
                         ord_qty,
                         0,
                         ord_prc,
                         ord_qty * ord_prc,
                         0);
            voltExecuteSQL();
        }

	return ClientResponse.SUCCESS;
	
    }
}
