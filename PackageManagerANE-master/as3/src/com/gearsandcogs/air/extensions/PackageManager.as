/**
 * Created by jhilton on 10/2/14.
 */
package com.gearsandcogs.air.extensions
{
    import flash.events.EventDispatcher;
    import flash.events.StatusEvent;
    import flash.external.ExtensionContext;

    public class PackageManager extends EventDispatcher
    {
        private var extContext:ExtensionContext;

        public function PackageManager() {
            extContext = ExtensionContext.createExtensionContext("com.gearsandcogs.air.extensions.PackageManager", "");
            if (!extContext) {
                throw new Error("PackageManager native extension is not supported on this platform.");
            }
            extContext.addEventListener(StatusEvent.STATUS, onStatus);
        }

        public function getSystemApps():void {
            trace("getSystemApps hit");
            extContext.call("GetSystemApps");
        }

        public function getUserApps():void {
            trace("getUserApps hit");
            extContext.call("GetUserApps");
        }

        public function installApp(app_id:String):void {
            trace("installApp hit");
            extContext.call("InstallApp", app_id);
        }

        public function runApp(app_id:String, params:Object = null):void {
            trace("runApp hit");
            if(params)
                params = JSON.stringify(params);
            else
                params = "{}";

            extContext.call("RunApp", app_id, params);
        }
		
		public function forceStopApp(app_id:String):void {
			trace("ForceStopApp hit");
			extContext.call("ForceStopApp", app_id);
		}
		
		public function clearDataAndCacheApp(app_id:String):void {
			trace("Clear data and cache hit");
			extContext.call("ClearDataAndCacheApp", app_id);
		}
		
        public function uninstallApp(app_id:String):void {
            trace("uninstallApp hit");
            extContext.call("UninstallApp", app_id);
        }

        private function onStatus(e:StatusEvent):void {
            var value:Object = e.level;
            try {
                value = JSON.parse(e.level);
            }
            catch (e:Error) {
                //unable to parse JSON from data
            }

            dispatchEvent(new PackageManagerEvent(e.code, false, false, value));
        }
    }
}
