(ns sicp-in-clojure.ex3
  (:require [sicp-in-clojure.ch3 :refer
             [cons car cdr set-car! set-cdr!]]
            [sicp-in-clojure.ch1]))

(defn accumulate [op init coll]
  (if (or (nil? coll) (= coll '()))
    init
    (op (car coll)
        (accumulate op init (cdr coll)))))

(defn to-list [z]
  (accumulate (fn [x xs] (conj xs x)) '() z))

; testing to-list
(= (to-list (cons 1 (cons 2 (cons 100 nil))))
   '(1 2 100))

(defn make-queue
  "Exercise 3.22"
  []
  (let
   [queue (cons nil nil)

    front-ptr
    (fn [] (car queue))

    rear-ptr
    (fn [] (cdr queue))

    empty?
    (fn [] (nil? (front-ptr)))

    set-front-ptr!
    (fn [item] (set-car! queue item))

    set-rear-ptr!
    (fn [item] (set-cdr! queue item))

    front
    (fn []
      (if (empty?)
        (throw (Exception. "Front called on empty queue"))
        (car (front-ptr))))

    insert!
    (fn [item]
      (let
       [new-pair (cons item nil)]
        (if (empty?)
          (do (set-front-ptr! new-pair)
              (set-rear-ptr! new-pair))
          (do (set-cdr! (rear-ptr) new-pair)
              (set-rear-ptr! new-pair)))
        nil))

    pop!
    (fn []
      (if (empty?)
        (throw (Exception. "Pop called on empty queue"))
        (let [frontval (front)]
          (set-front-ptr! (cdr (front-ptr)))
          frontval)))

    dispatch
    (fn [m]
      (cond (= m 'empty?) empty?
            (= m 'front) front
            (= m 'insert!) insert!
            (= m 'pop!) pop!
            (= m 'to-list) (fn [] (to-list (front-ptr)))
            :else (throw (Exception. "Unknown request"))))]

    dispatch))

(comment
  (def q1 (make-queue))
  ((q1 'insert!) 'a)
  ((q1 'to-list))
  ((q1 'insert!) 'b)
  ((q1 'to-list))
  ((q1 'insert!) 'c)
  ((q1 'to-list))
  ((q1 'pop!))  ; repeat this
  ((q1 'front))
  ((q1 'empty?)))

