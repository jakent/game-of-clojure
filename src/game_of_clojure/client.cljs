(ns game-of-clojure.client
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            [game-of-clojure.core :as core]))

(defn calculate-count [dimension]
  (-> dimension (/ 30) int dec))

(defn discover-dimensions []
  {:x-count (calculate-count (.-innerWidth js/window))
   :y-count (calculate-count (.-innerHeight js/window))})

(def dimensions (r/atom (discover-dimensions)))

(defn on-resize [_]
  (reset! dimensions (discover-dimensions)))

(defonce living (r/atom core/r-pentomino))

(defonce life (js/setInterval
                #(swap! living core/tick) 500))

(defn cell [{:keys [alive]}]
  [:div {:style {:height          30
                 :width           30
                 :flex            1
                 :margin          1
                 :backgroundColor (if alive "black" "wheat")}}])

(defn row [{:keys [y living]}]
  [:div {:style {:display "flex"}}
   (map (fn [x] [cell {:key x :alive (contains? living [x y])}])
        (range (:x-count @dimensions)))])

(defn grid []
  [:div {:style {:display       "flex"
                 :flexDirection "column"}}
   (let [living  @living]
     (map (fn [y] [row {:key y :y y :living living}])
          (range (:y-count @dimensions))))])

(defn ^:export run []
  (rdom/render [grid]
               (.getElementById js/document "app"))
  (.addEventListener js/window "resize" on-resize))