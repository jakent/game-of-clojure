(ns game-of-clojure.client
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            [game-of-clojure.core :as core]))

(defonce living (r/atom core/r-pentomino))

(defonce life (js/setInterval
                #(swap! living core/tick) 500))

(defn cell [{:keys [alive]}]
  [:div {:style {:height          "30px"
                 :width           "30px"
                 :margin          "1px"
                 :backgroundColor (if alive "black" "wheat")}}])

(defn row [{:keys [y living]}]
  [:div {:style {:display "flex"}}
   (map (fn [x] [cell {:key x :alive (contains? living [x y])}])
        (range 40))])

(defn home []
  [:div {:style {:display       "flex"
                 :flexDirection "column"}}
   (let [living @living]
     (map (fn [y] [row {:key y :y y :living living}])
          (range 20)))
   ])

(defn ^:export run []
  (rdom/render [home]
               (js/document.getElementById "app")))