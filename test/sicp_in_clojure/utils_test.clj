(ns sicp-in-clojure.utils-test
  (:require [clojure.test :refer :all]
            [sicp-in-clojure.utils :refer [all? falsy? truthy?]]))

(deftest falsy?-test
  (testing "falsy?"
    (is (falsy? nil))
    (is (falsy? false))
    (is (not (falsy? true)))
    (is (not (falsy? 1)))
    (is (not (falsy? 0)))))

(deftest truthy?-test
  (testing "truthy? is opposite of falsy?"
    (let [testvals [0 1 -1 true false nil "" "0" "1" :0 :nil :false :true]]
      (is (= (into [] (map falsy? testvals))
             (into [] (map #(not (truthy? %)) testvals)))))))

(deftest all?-test
  (testing "all?"
    (is (truthy? (all? [1 true 10])))
    (is (truthy? (all? (repeat 1000 true))))
    (is (truthy? (all? [])))
    (is (falsy? (all? [nil])))
    (is (falsy? (all? [false true])))
    (is (falsy? (all? [true nil true])))
    (is (falsy? (all? (conj (repeat 1000 true) false))))))

