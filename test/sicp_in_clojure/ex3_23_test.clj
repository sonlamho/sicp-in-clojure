(ns sicp-in-clojure.ex3-23-test
  (:require [clojure.test :refer :all]
            [sicp-in-clojure.ex3-23 :refer
             [make-deque deque-to-list empty-deque? front-deque rear-deque
              front-insert-deque! rear-insert-deque!
              front-delete-deque! rear-delete-deque!]]))

(deftest make-deque-test
  (testing "deque operations"
    (let [d (make-deque)]
      ; empty
      (is (= '() (deque-to-list d)))
      (is (empty-deque? d))
      (is (= 'empty (front-deque d)))
      (is (= 'empty (rear-deque d)))

      ; 1 element
      (front-insert-deque! d 1)
      (is (= (list 1) (deque-to-list d)))
      (is (not (empty-deque? d)))
      (is (= 1 (front-deque d)))
      (is (= 1 (rear-deque d)))

      ; 2 elements
      (front-insert-deque! d 2)
      (is (= (list 2 1) (deque-to-list d)))
      (is (not (empty-deque? d)))
      (is (= 2 (front-deque d)))
      (is (= 1 (rear-deque d)))

      ; more elements
      (front-insert-deque! d 3)
      (is (= (list 3 2 1) (deque-to-list d)))
      (rear-insert-deque! d :a)
      (is (= (list 3 2 1 :a) (deque-to-list d)))
      (rear-insert-deque! d :b)
      (is (= (list 3 2 1 :a :b) (deque-to-list d)))

      ; removing elements
      (front-delete-deque! d)
      (is (= (list 2 1 :a :b) (deque-to-list d)))
      (rear-delete-deque! d)
      (is (= (list 2 1 :a) (deque-to-list d)))
      (front-delete-deque! d)
      (is (= (list 1 :a) (deque-to-list d)))
      (rear-delete-deque! d)
      (is (= (list 1) (deque-to-list d)))
      ; back to empty
      (rear-delete-deque! d)
      (is (= '() (deque-to-list d)))
      (is (empty-deque? d))

      ; deleting when empty
      (rear-delete-deque! d)
      (is (= '() (deque-to-list d)))
      (is (empty-deque? d))

      (front-delete-deque! d)
      (is (= '() (deque-to-list d)))
      (is (empty-deque? d)))))
