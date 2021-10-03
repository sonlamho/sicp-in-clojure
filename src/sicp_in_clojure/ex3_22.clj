(ns sicp-in-clojure.ex3-22
  (:require [sicp-in-clojure.ch3 :refer
             [cons car cdr set-car! set-cdr!]]))
; Note: cons defined in sicp-in-clojure.ch3 is different from Clojure's built-in cons
; We need this because Clojure's built-in cons cannot be made mutable

(defn accumulate [op init coll]
  (if (or (nil? coll) (= coll '()))
    init
    (op (car coll)
        (accumulate op init (cdr coll)))))

(defn to-list [z]
  (accumulate (fn [x xs] (conj xs x)) '() z))

(assert (= (to-list (cons 1 (cons 2 (cons 3 (cons 4  nil)))))
           (list 1 2 3 4)))

(defn make-queue
  "Exercise 3.22"
  []
  (let
   [queue (cons nil nil)

    front-ptr
    (fn [] (car queue))

    ; TODO: define the missing functions

    dispatch
    (fn [m]
      ; More modern "method" names for queue operations
      (cond (= m 'empty?) (fn [& _] 'TODO)
            (= m 'front) (fn [& _] 'TODO)
            (= m 'insert!) (fn [& _] 'TODO)
            (= m 'pop!) (fn [& _] 'TODO)
            (= m 'to-list) (fn [] (to-list (front-ptr)))
            :else (throw (Exception. "Unknown request"))))]

    dispatch))

