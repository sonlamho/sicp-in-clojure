(ns sicp-in-clojure.ex1-11-test
  (:require [clojure.test :refer :all]
            [sicp-in-clojure.utils :refer [all?]]
            [sicp-in-clojure.ex1-11 :refer [f-recursive f-iter]]))

(def results-15 [0 1 2 4 11 25 59 142 335 796 1892 4489 10661 25315 60104])

(deftest f-recursive-test
  (testing "f-recursive correctness"
    (is (all? (map ==
                   results-15
                   (map f-recursive (range 15)))))))

(deftest f-iter-test
  (testing "f-iter correctness"
    (is (all? (map ==
                   results-15
                   (map f-iter (range 15))))))
  (testing "specific large values"
    (is (== 61354575194 (f-iter 30)))
    (is (== 11937765839880230562825561449279733086N (f-iter 100)))))
