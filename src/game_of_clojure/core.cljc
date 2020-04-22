(ns game-of-clojure.core)

(def blinker #{[0 1] [1 1] [2 1]})
(def boat #{[1 1] [2 1] [1 2] [3 2] [2 3]})
(def glider #{[1 0] [2 1] [0 2] [1 2] [2 2]})
(def beacon #{[1 1] [2 1] [1 2] [4 3] [3 4] [4 4]})
(def r-pentomino #{[7 11] [6 12] [7 12] [8 12] [6 13]})
(def combination #{[0 12] [1 12] [2 12] [1 6] [2 7] [0 8] [1 8] [2 8]})
(def gosper-glider-gun #{[1 5] [2 5] [1 6] [2 6] [11 5] [11 6] [11 7] [12 4]
                         [12 8] [13 3] [13 9] [14 3] [14 9] [15 6] [16 4]
                         [16 8] [17 5] [17 6] [17 7] [18 6] [21 3] [21 4]
                         [21 5] [22 3] [22 4] [22 5] [23 2] [23 6] [25 1]
                         [25 2] [25 6] [25 7] [35 3] [35 4] [36 3] [36 4]})


(defn transform [alive
                 living-neighborhoods]
  (cond
    (= living-neighborhoods 3) :alive
    (and (= living-neighborhoods 2) alive) :alive
    :else :dead))

(defn neighbors [[x y]]
  (->> (for [x2 [-1 0 1]
             y2 [-1 0 1]
             :when (not= x2 y2 0)]
         (vector (+ x x2) (+ y y2)))))

(defn tick [alive-cells]
  (->> (mapcat neighbors alive-cells)
       frequencies
       (map (fn [[position living-neighbors]]
              [(transform (contains? alive-cells position) living-neighbors)
               position]))
       (group-by first)
       :alive
       (map second)
       (into #{})))

(defn life [living-cells]
   (lazy-seq (cons living-cells (life (tick living-cells)))))

