(ns sicp-in-clojure.ex3-03-test
  (:require [clojure.test :refer :all]
            [sicp-in-clojure.ex3-03 :refer [make-account]]))

(deftest make-account-test
  (testing "good password allowing withdraw and deposit"
    (let [acc (make-account 100 'pass123)]
      (is (= 80 ((acc 'pass123 'withdraw) 20)))
      (is (= 140 ((acc 'pass123 'deposit) 60)))
      (is (= "Insufficient funds" ((acc 'pass123 'withdraw) 141)))
      (is (= 40 ((acc 'pass123 'withdraw) 100)))))
  (testing "accounts are separate"
    (let [acc1 (make-account 100 'pass1)
          acc2 (make-account 100 'pass2)]
      (is (= 80 ((acc1 'pass1 'withdraw) 20)))
      (is (= 150 ((acc2 'pass2 'deposit) 50)))
      (is (= 10 ((acc1 'pass1 'withdraw) 70)))
      (is (= 50 ((acc2 'pass2 'withdraw) 100)))))
  (testing "bad password returns a message"
    (let [acc (make-account 100 'pass123)]
      (is (= "Incorrect password" ((acc 'badpass 'withdraw) 20))))))
