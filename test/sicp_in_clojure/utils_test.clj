(ns sicp-in-clojure.utils-test
  (:require [clojure.test :refer :all]
            [sicp-in-clojure.utils :refer [all? falsy? truthy?]]))

(deftest all?-test
  (testing "all?"
    (is (truthy? (all? [1 true 10])))
    (is (truthy? (all? (repeat 1000 true))))
    (is (truthy? (all? [])))
    (is (falsy? (all? [nil])))
    (is (falsy? (all? [false true])))
    (is (falsy? (all? [true nil true])))
    (is (falsy? (all? (conj (repeat 1000 true) false))))
    ))

