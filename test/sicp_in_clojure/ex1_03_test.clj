(ns sicp-in-clojure.ex1-03-test
  (:require [clojure.test :refer :all]
            [sicp-in-clojure.ex1-03 :refer [sum-square-larger]]))

(deftest sum-square-larger-test
  (testing "returning sum of squares of 2 largest numbers"
    (is (== 4 (sum-square-larger 0 2 -2)))
    (is (== 25 (sum-square-larger 3 1 4)))
    (is (== 10 (sum-square-larger -3 1 -100)))
    (is (== 10 (sum-square-larger -3 1 -100)))
    (is (== 160 (sum-square-larger 4 -2 12)))))
