(ns sicp-in-clojure.ex3-23
  (:require [sicp-in-clojure.ch3 :refer
             [cons car cdr set-car! set-cdr!]]))
; Note: cons defined in sicp-in-clojure.ch3 is different from Clojure's built-in cons
; We need this because Clojure's built-in cons cannot be made mutable


(defn make-deque
  "Exercise 3.23"
  []
  (cons nil nil))

(defn front-ptr [deq] (car deq))
(defn rear-ptr [deq] (cdr deq))
(defn empty-deque? [deq]
  (or (nil? (front-ptr deq)) (nil? (rear-ptr deq))))
(defn set-front-ptr! [deque item] (set-car! deque item))
(defn set-rear-ptr! [deque item] (set-cdr! deque item))

(defn make-deque-link
  "deque items are 3-tuples (value, forward-ptr, backward-ptr)"
  [v f b]
  (cons v (cons f (cons b nil))))

(defn get-value [deq-link] (car deq-link))
(defn get-forward-ptr [deq-link] (car (cdr deq-link)))
(defn get-backward-ptr [deq-link] (car (cdr (cdr deq-link))))
(defn set-forward-ptr! [deq-link ptr] (set-car! (cdr deq-link) ptr))
(defn set-backward-ptr! [deq-link ptr] (set-car! (cdr (cdr deq-link)) ptr))

(defn front-deque [deq]
  (if (empty-deque? deq)
    'empty
    (get-value (front-ptr deq))))

(defn rear-deque [deq]
  (if (empty-deque? deq)
    'empty
    (get-value (rear-ptr deq))))

(defn all-but-front [deq]
  (cons (get-forward-ptr (front-ptr deq))
        (rear-ptr deq)))

(defn deque-to-list [deq]
  (cond (empty-deque? deq)
        '()
        (identical? (front-ptr deq) (rear-ptr deq))
        (list (front-deque deq))
        :else
        (conj (deque-to-list
               (all-but-front deq))
              (front-deque deq))))

(defn front-push-deque! [deq item]
  (let [old-front (front-ptr deq)
        new-front (make-deque-link item old-front nil)]
    (if (empty-deque? deq)
      (set-rear-ptr! deq new-front)
      (set-backward-ptr! old-front new-front))
    (set-front-ptr! deq new-front)
    nil))

(defn rear-push-deque! [deq item]
  (let [old-rear (rear-ptr deq)
        new-rear (make-deque-link item nil old-rear)]
    (if (empty-deque? deq)
      (set-front-ptr! deq new-rear)
      (set-forward-ptr! old-rear new-rear))
    (set-rear-ptr! deq new-rear)
    nil))

(defn front-pop-deque! [deq]
  (if (empty-deque? deq)
    'empty
    (let [old-front (front-ptr deq)
          new-front (get-forward-ptr old-front)]
      (when (not (nil? new-front))
        (set-backward-ptr! new-front nil))
      (set-front-ptr! deq new-front)
      (get-value old-front))))

(defn rear-pop-deque! [deq]
  (if (empty-deque? deq)
    'empty
    (let [old-rear (rear-ptr deq)
          new-rear (get-backward-ptr old-rear)]
      (when (not (nil? new-rear))
        (set-forward-ptr! new-rear nil))
      (set-rear-ptr! deq new-rear)
      (get-value old-rear))))

(def front-insert-deque! front-push-deque!)
(def rear-insert-deque! rear-push-deque!)
(def front-delete-deque! front-pop-deque!)
(def rear-delete-deque! rear-pop-deque!)

(comment
  (def d (make-deque))
  (deque-to-list d)
  (front-push-deque! d 1)
  (deque-to-list d)
  (front-push-deque! d 2)
  (deque-to-list d)
  (front-push-deque! d 3)
  (deque-to-list d)
  (rear-push-deque! d :a)
  (deque-to-list d)
  (rear-push-deque! d :b)
  (deque-to-list d)
  (front-pop-deque! d)  ; repeat this
  (deque-to-list d)
  (rear-pop-deque! d)  ; repeat this
  )

