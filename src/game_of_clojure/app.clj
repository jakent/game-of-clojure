(ns game-of-clojure.app
  (:require [game-of-clojure.core :as core])
  (:gen-class))

(defn print-grid [width height living-cells]
  (print "\033[H\033[2J")

  (doseq [y (range height)
          x (range width)]
    (do
      (print (if (contains? living-cells [x y])
               " * "
               "   "))
      (if (= (inc x) width)
        (println))))

  (Thread/sleep 500))

(defn draw [life-seq width height]
  (map (partial print-grid width height) life-seq))

(defn -main [& args]
  (dorun (draw (core/life core/gosper-glider-gun) 40 20)))