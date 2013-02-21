(defproject leiningen.classloading.hook "0.0.1-SNAPSHOT"
  :description "Eclipse ClassLoadingHook for Leiningen plugin projects"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.0-RC6"]
                 [leiningen "2.0.0"]
                 [org.eclipse.jetty/jetty-server "7.6.1.v20120215"]]
  :source-paths []
  :plugins [[ccw/lein-ccw-deps "0.1.0-SNAPSHOT"]])