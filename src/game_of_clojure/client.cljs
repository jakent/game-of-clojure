(ns game-of-clojure.client
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            [game-of-clojure.core :as core]))


(def origin (r/atom [-1 -1]))

(defn on-key-down [event]
  (swap! origin (fn [[x y]]
                  (case (.-keyCode event)
                    39 [(inc x) y]
                    37 [(dec x) y]
                    38 [x (dec y)]
                    40 [x (inc y)]
                    [x y]))))

(defn calculate-count [dimension]
  (-> dimension (/ 30) int dec))

(defn discover-dimensions []
  {:x-count (calculate-count (.-innerWidth js/window))
   :y-count (calculate-count (.-innerHeight js/window))})

(def dimensions (r/atom (discover-dimensions)))

(defn on-resize [e]
  (reset! dimensions (discover-dimensions)))

(defonce living (r/atom core/r-pentomino))

(defonce life (js/setInterval
                #(swap! living core/tick) 50))

(defn calculate-range [offset count]
  (range offset (+ count offset)))

(defn cell [{:keys [alive]}]
  [:div {:style {:height          30
                 :width           30
                 :flex            1
                 :margin          1
                 :backgroundColor (if alive "black" "wheat")}}])

(defn row [{:keys [y living]}]
  [:div {:style {:display "flex"}}
   (map (fn [x] [cell {:key x :alive (contains? living [x y])}])
        (calculate-range (first @origin) (:x-count @dimensions)))])

(defn grid []
  [:div {:style {:display       "flex"
                 :flexDirection "column"
                 :overflow      "hidden"}}
   (let [living @living]
     (map (fn [y] [row {:key y :y y :living living}])
          (calculate-range (second @origin) (:y-count @dimensions))))])

(defn ^:export run []
  (rdom/render [grid]
               (.getElementById js/document "app"))
  (.addEventListener js/window "resize" on-resize)
  (.addEventListener js/document "keydown" on-key-down))