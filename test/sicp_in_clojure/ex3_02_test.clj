(ns sicp-in-clojure.ex3-02-test
  (:require [clojure.test :refer :all]
            [sicp-in-clojure.ex3-02 :refer [make-monitored]]))

(defn sqrt [x] (Math/sqrt x))
(defn square [x] (* x x))

(deftest make-monitored-test
  (testing "keeping count correctly"
    (let [s (make-monitored sqrt)]
      (is (= (s 'how-many-calls?) 0))
      (s 64)
      (is (= (s 'how-many-calls?) 1))
      (into [] (map s (range 15)))
      (is (= (s 'how-many-calls?) 16))))
  (testing "reseting functionality"
    (let [s2 (make-monitored square)]
      (into [] (map s2 (range 123)))
      (is (= (s2 'how-many-calls?) 123))
      (s2 'reset-count)
      (is (= (s2 'how-many-calls?) 0))
      (into [] (map s2 (range 16)))
      (is (= (s2 'how-many-calls?) 16))
      (s2 'reset-count)
      (is (= (s2 'how-many-calls?) 0))))
  (testing "wrapped function behaves the same way"
    (let [s (make-monitored sqrt)
          s2 (make-monitored square)]
      (is (= (s2 1.234) (square 1.234)))
      (is (= (s2 9876) (square 9876))))))

