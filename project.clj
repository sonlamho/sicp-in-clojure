(defproject sicp-in-clojure "0.1.0-SNAPSHOT"
  :description "Exercise solutions for SICP written in Clojure"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3"]]
  :plugins [[cider/cider-nrepl "0.25.3"]
            [lein-cljfmt/lein-cljfmt "0.8.0"]]
  :main ^:skip-aot sicp-in-clojure.core
  :repl-options {:init-ns sicp-in-clojure.core}
  :profiles {:uberjar {:aot :all}})
