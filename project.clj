(defproject game-of-clojure "0.1.0-SNAPSHOT"
  :description "game of life"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :main game-of-clojure.app

  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/clojurescript "1.10.597"]
                 [reagent "0.10.0"]
                 [figwheel "0.5.18"]]

  :plugins [[lein-auto "0.1.3"]
            [lein-cljsbuild "1.1.8"]
            [lein-figwheel "0.5.18"]]

  :source-paths ["src"]
  :resource-paths ["resources" "target"]
  :clean-targets ^{:protect false} [:target-path]

  :profiles {:dev {:repl-options {:init-ns game-of-clojure.core-test}
                   :cljsbuild    {:builds {:client
                                           {:figwheel {:on-jsload "game_of_clojure.client/run"}
                                            :compiler {:main          "game_of_clojure.client"
                                                       :optimizations :none}}}}}}

  :figwheel {:repl             false
             :http-server-root "public"}

  :cljsbuild {:builds {:client
                       {:source-paths ["src"]
                        :compiler     {:output-dir "target/public/client"
                                       :asset-path "client"
                                       :output-to  "target/public/client.js"}}}}

  :repl-options {:init-ns game-of-clojure.core})
