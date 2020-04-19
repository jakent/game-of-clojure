(defproject game-of-clojure "0.1.0-SNAPSHOT"
  :description "game of life"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :main game-of-clojure.app

  :dependencies [[org.clojure/clojure "1.10.0"]]

  :source-paths ["src"]

  :profiles {:dev {:repl-options {:init-ns game-of-clojure.core-test}}}

  :plugins [[lein-auto "0.1.3"]]

  :repl-options {:init-ns game-of-clojure.core})
