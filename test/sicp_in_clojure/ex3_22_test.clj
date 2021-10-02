(ns sicp-in-clojure.ex3-22-test
  (:require [clojure.test :refer :all]
            [sicp-in-clojure.ex3-22 :refer [make-queue]]))

(deftest make-queue-test
  (testing "queue operations"
    (let [q (make-queue)]
      (is ((q 'empty?)))

      ((q 'insert!) 'a)
      (is (= '(a) ((q 'to-list))))
      ((q 'insert!) 'b)
      (is (= '(a b) ((q 'to-list))))
      ((q 'insert!) 'c)
      (is (= '(a b c) ((q 'to-list))))

      (is (not ((q 'empty?))))

      ((q 'pop!))
      (is (= '(b c) ((q 'to-list))))
      ((q 'pop!))
      (is (= '(c) ((q 'to-list))))
      ((q 'pop!))
      (is (= '() ((q 'to-list))))

      (is ((q 'empty?))))))
