(ns sicp-in-clojure.ex3-07-test
  (:require [clojure.test :refer :all]
            [sicp-in-clojure.utils :refer [all?]]
            [sicp-in-clojure.ex3-07 :refer [make-joint]]
            [sicp-in-clojure.ex3-03 :refer [make-account]]))

(deftest make-joint-test
  (testing "separated passwords"
    (let [peter-acc (make-account 100 'open-sesame)
          paul-acc (make-joint peter-acc 'open-sesame 'rosebud)]
      (is (= 200 ((peter-acc 'open-sesame 'deposit) 100)))
      (is (= "Incorrect password" ((paul-acc 'open-sesame 'deposit) 10)))
      (is (= "Incorrect password" ((peter-acc 'rosebud 'deposit) 10)))))

  (testing "Same account balance"
    (let [peter-acc (make-account 100 'open-sesame)
          paul-acc (make-joint peter-acc 'open-sesame 'rosebud)]
      (is (= 200 ((peter-acc 'open-sesame 'deposit) 100)))
      (is (= 300 ((paul-acc 'rosebud 'deposit) 100)))
      (is (= 230 ((peter-acc 'open-sesame 'withdraw) 70)))
      (is (= 215 ((paul-acc 'rosebud 'withdraw) 15))))))

