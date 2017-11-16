package billingAccount;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class BillingAccountHelper {
    
	/** The database connection to use */
	private final Connection conn;

	/**
	 * Constructor
	 * @param conn The database connection to use
	 */
    public BillingAccountHelper ( final Connection conn ) {
        this.conn = conn;
    }

    /**
     * Prints out billing account.
     *
     * @param scan
     *            used for user input.
     */
    public void getBillingAccount ( final Scanner scan ) {
        final BillingAccountDB bdb = new BillingAccountDB( conn );
        int id;
        while ( true ) {
            System.out.println( "Enter Patient id or b-back:" );
            System.out.println( ">" );

            final String in = scan.nextLine().toLowerCase();
            if ( in.equals( "b" ) || in.equals( "back" ) ) {
                return;
            }
            try {
                id = Integer.parseInt( in );
                final ArrayList<BillingAccount> bas = bdb.getBillingAccount( id );

                if ( bas == null || bas.size() == 0 ) {
                    System.out.println( "There is no billing account for that id" );
                }
                else {
                    System.out.printf( "Patient ID: %d\n", id );
                    for ( final BillingAccount b : bas ) {
                        System.out.printf( "\tVisit date: %s Consultation fee: %f Test fee: %f Treatment fee: %f\n",
                                b.getDate().toString(), b.getConsultingFee(), b.getTestFee(), b.getTreatmentFee() );

                    }
                }
            }
            catch ( final NumberFormatException e ) {
                System.out.println( "Invalid id" );
            }
        }
    }

}
