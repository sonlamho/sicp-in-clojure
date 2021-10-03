(ns sicp-in-clojure.ex3-23
  (:require [sicp-in-clojure.ch3 :refer
              [cons car cdr set-car! set-cdr!]]))
; Note: cons defined in sicp-in-clojure.ch3 is different from Clojure's built-in cons
; We need this because Clojure's built-in cons cannot be made mutable


(defn make-deque
  "Exercise 3.23"
  []
  'TODO)

(defn empty-deque? [deq]
  'TODO)

(defn front-deque [deq]
  'TODO)

(defn rear-deque [deq]
  'TODO)

(defn deque-to-list [deq]
  'TODO)

(defn front-push-deque! [deq item]
  'TODO)

(defn rear-push-deque! [deq item]
  'TODO)

(defn front-pop-deque! [deq]
  'TODO)

(defn rear-pop-deque! [deq]
  'TODO)

(def front-insert-deque! front-push-deque!)
(def rear-insert-deque! rear-push-deque!)
(def front-delete-deque! front-pop-deque!)
(def rear-delete-deque! rear-pop-deque!)
