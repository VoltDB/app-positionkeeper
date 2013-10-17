package procedures;

import java.util.Date;
import org.voltdb.*;
import org.voltdb.client.ClientResponse;

public class PriceInsert extends VoltProcedure {

    public final SQLStmt insertPrice = new SQLStmt(
        "INSERT INTO prc (codprc, prc_sec, prc_price, prc_ts) VALUES (?, ?, ?, ?);");

    public final SQLStmt updatePos = new SQLStmt(
        "UPDATE pos SET " +
        "  pos_prc = ?," + 
        "  pos_cum_val_ord = pos_cum_qty_ord * ?," +
        "  pos_cum_val_exe = pos_cum_qty_exe * ?" +
        " WHERE codsec = ?;");

    public VoltTable[] run( int     codprc,
                            int     prc_sec,
                            double  prc_price,
                            Date    prc_ts
                            ) throws VoltAbortException {


        voltQueueSQL(insertPrice, 
                     codprc, 
                     prc_sec,
                     prc_price,
                     prc_ts);
        
        voltQueueSQL(updatePos,
                     prc_price,
                     prc_price,
                     prc_price,
                     prc_sec);

        return voltExecuteSQL();

    }
}
