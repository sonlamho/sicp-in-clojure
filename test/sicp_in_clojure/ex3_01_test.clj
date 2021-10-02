(ns sicp-in-clojure.ex3-01-test
  (:require [clojure.test :refer :all]
            [sicp-in-clojure.ex3-01 :refer [make-accumulator]]))

(deftest make-accumulator-test
  (testing "Separate accumulator instances"
    (let [A (make-accumulator 5)
          B (make-accumulator 100)]
      (is (= 5 (A 0)))
      (is (= 100 (B 0)))
      (is (= 15 (A 10)))
      (is (= 16 (A 1)))
      (is (= 110 (B 10)))
      (is (= 150 (B 40))))))
