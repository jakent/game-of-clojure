(ns game-of-clojure.core-test
  (:require [clojure.test :refer :all]
            [game-of-clojure.core :as core]))

(deftest alive-cell-transformations
  (is (= :dead (core/transform :alive 1)))
  (is (= :alive (core/transform :alive 2)))
  (is (= :alive (core/transform :alive 3)))
  (is (= :dead (core/transform :alive 4))))

(deftest dead-cell-transformations
  (is (= :dead (core/transform false 1)))
  (is (= :dead (core/transform false 2)))
  (is (= :alive (core/transform false 3)))
  (is (= :dead (core/transform false 4))))

(deftest my-neighbors
  (is (= (set (core/neighbors [0 0]))
         #{[0 1] [1 1] [1 0]}))
  (is (= (set (core/neighbors [1 1]))
         #{[0 0] [1 0] [2 0] [2 1] [2 2] [1 2] [0 2] [0 1]})))

(deftest no-movement
  (is (= #{} (core/tick #{})))
  (is (= #{[0 0] [0 1] [1 0] [1 1]} (core/tick #{[0 0] [0 1] [1 0] [1 1]}))))

(deftest some-movement
  (is (= #{[0 0]} (core/tick #{[1 1] [1 0] [0 1]}))))