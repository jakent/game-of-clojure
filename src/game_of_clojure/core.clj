(ns game-of-clojure.core)

(defn transform [status
                 living-neighborhoods]
  (cond
    (= living-neighborhoods 3) :alive
    (and (= living-neighborhoods 2)
         (= status :alive)) :alive
    :else :dead))

(defn neighbors [[x y]]
  (->> (for [x2 [-1 0 1]
             y2 [-1 0 1]
             :when (not= x2 y2 0)]
         (vector x2 y2))
       (map (fn [[x2 y2]]
              [(+ x x2) (+ y y2)]))
       (filter (fn [[x2 y2]]
                 (and (<= 0 x2) (<= 0 y2))))))

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

