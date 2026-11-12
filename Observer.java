/**
 * // -------------------------------------------------------------------------
/**
 *  Observer interface for Craze Coffee 
 *  Recieves notifications when model data changes
 * 
 *  @author sydneyyang
 *  @version Nov 12, 2026
 */
public interface Observer
{
    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    void onModelChanged();
    /**
     * Called when the customer line changes
     */
    void updateLine();
    
    /**
     * Called when player info changes
     * 
     * @param strikes number of strikes
     * @param money players total money
     */
    void updatePlayer(int strikes, int money);
}
