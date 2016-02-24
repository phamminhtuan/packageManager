/**
 * Created by jhilton on 10/2/14.
 */
package com.gearsandcogs.air.extensions
{
    import flash.events.Event;

    public class PackageManagerEvent extends Event
    {
    public static var ERROR_INSTALL_APP:String = "ERROR_INSTALL_APP";
    public static var ERROR_RUN_APP:String = "ERROR_RUN_APP";
    public static var ERROR_UNINSTALL_APP:String = "ERROR_UNINSTALL_APP";
	public static var ERROR_FORCE_STOP_APP:String = "ERROR_FORCE_STOP_APP";
	public static var ERROR_CLEAR_DATA_AND_CACHE_APP:String = "ERROR_CLEAR_DATA_AND_CACHE_APP";

    public static var GET_SYSTEM_APPS:String = "GET_SYSTEM_APPS";
    public static var GET_USER_APPS:String = "GET_USER_APPS";

    public static var SUCCESS_RUN_APP:String = "SUCCESS_RUN_APP";
    public static var SUCCESS_INSTALL_APP:String = "SUCCESS_INSTALL_APP";
    public static var SUCCESS_UNINSTALL_APP:String = "SUCCESS_UNINSTALL_APP";
	public static var SUCCESS_FORCE_STOP_APP:String = "SUCCESS_FORCE_STOP_APP";
	public static var SUCCESS_CLEAR_DATA_AND_CACHE_APP:String = "SUCCESS_CLEAR_DATA_AND_CACHE_APP";

        public var param        :Object;
        public function PackageManagerEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false,param:Object="")
        {
            super(type, bubbles, cancelable);
            this.param = param;
        }
    }
}
