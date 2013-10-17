package procedures;

import org.voltdb.*;
import org.voltdb.client.ClientResponse;

public class TradeInsert extends VoltProcedure {

    public final SQLStmt insertTrade = new SQLStmt(
        "INSERT INTO trd (codtrd, trd_cnt, trd_sec, trd_qty, trd_prc) VALUES (?, ?, ?, ?, ?);");

    public final SQLStmt updatePos = new SQLStmt(
        "UPDATE pos SET " +
        "  pos_cum_qty_exe = pos_cum_qty_exe + ?," +
        "  pos_cum_val_exe = pos_cum_qty_exe * pos_prc" +
        " WHERE codsec = ? AND codcnt = ?;");

    public final SQLStmt insertPos = new SQLStmt(
        "INSERT INTO pos VALUES (" +
        "?,?,?,?,?,?,?" +
        ");");

    public long run( int     codtrd,
                     int     trd_cnt,
                     int     trd_sec,
                     int     trd_qty,
                     double  trd_prc
		     ) throws VoltAbortException {


        voltQueueSQL(insertTrade, 
                     codtrd, 
                     trd_cnt,
                     trd_sec,
                     trd_qty,
                     trd_prc);
        
        voltQueueSQL(updatePos,
                     trd_qty,
                     trd_sec,
                     trd_cnt);

        VoltTable results1[] = voltExecuteSQL();
        
        long rowsAffected = results1[1].asScalarLong();

        if (rowsAffected == 0) {
            // then insert
            voltQueueSQL(insertPos,
                         trd_cnt,
                         trd_sec,
                         0,
                         trd_qty,
                         trd_prc,
                         0,
                         trd_qty * trd_prc
                         );
            voltExecuteSQL();
        }

	return ClientResponse.SUCCESS;
	
    }
}
