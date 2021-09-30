(ns sicp-in-clojure.ex3
  (:require [sicp-in-clojure.ch3]
            [sicp-in-clojure.ch1]
            ))

; --------------------------------------------------
(defn make-accumulator
  "Exercise 3.1"
  [init]
  (let [v (atom init)]
    (fn [amount] (swap! v #(+ % amount)))))

(comment
"Test code for Ex3.1"
(def A (make-accumulator 5))
(def B (make-accumulator 100))
(= 15 (A 10))
(= 16 (A 1))
(= 110 (B 10))
(= 26 (A 10))
(= 150 (B 40))
)

; --------------------------------------------------
(defn make-monitored
  "Exercise 3.2"
  [fun]
  (let
    [counter (atom 0)
     monitored
      (fn ([x]
            (cond
              (= x 'how-many-calls?) @counter
              (= x 'reset-count) (reset! counter 0)
              :else
                (do (swap! counter inc)
                    (fun x))))
          ([x & xs]
            (swap! counter inc)
            (apply fun x xs))
          ([]
            (swap! counter inc)
            (fun)))]
    monitored))

(defn sqrt [x] (Math/sqrt x))
(comment
"Test code for Ex3.2"
(def s (make-monitored sqrt))
(= (s 'how-many-calls?) 0)
(s 49)
(println (s 'how-many-calls?))
(s 100)
(println (s 'how-many-calls?))
(s 'reset-count)
(println (s 'how-many-calls?))
(take 77 (map s (range)))
(println (s 'how-many-calls?))
)

; --------------------------------------------------
(defn make-account
  "Exercise 3.3"
  [bal password]
  (let
    [balance (atom bal)
     withdraw
        (fn [amount]
          (if (>= @balance amount)
            (swap! balance #(- % amount))
            "Insufficient funds"))
     deposit
        (fn [amount] (swap! balance #(+ % amount)))
     dispatch
        (fn [pwd m]
          (if (= pwd password)
            (cond (= m 'withdraw) withdraw
                  (= m 'deposit) deposit
                  :else (throw (Exception. "Unknown request")))
            (fn [& _] "Incorrect password")))]
    dispatch))

(comment
(def acc1 (make-account 100 'ac1pass))
(def acc2 (make-account 100 'ac2pass))
((acc1 'ac1pass 'withdraw) 20)
((acc2 'ac2pass 'withdraw) 10)
((acc1 'wrongpwd 'withdraw) 30)
((acc1 'ac1pass 'withdraw) 30)
((acc2 'ac2pass 'deposit) 100)
((acc1 'ac1pass 'deposit) 50)
((acc1 'ac1pass 'depo) 10000000)
((acc2 'wrongpwd 'withdraw) 1000)
((acc2 'ac2pass 'withdraw) 1000)
)

; --------------------------------------------------
(defn make-account
  "Exercise 3.4"
  [bal password]
  (let
    [
      balance (atom bal)
      num-tries (atom 0)
      MAX-N-TRIES 7
      ;------
      withdraw
        (fn [amount]
          (if (>= @balance amount)
            (swap! balance #(- % amount))
            "Insufficient funds"))
      deposit
        (fn [amount] (swap! balance #(+ % amount)))
      bad-password
        (fn [& _]
          (if (< @num-tries MAX-N-TRIES)
              (do (swap! num-tries inc)
                  "Incorrect password")
              "Call the cops!"))
      dispatch
        (fn [pwd m]
          (if (= pwd password)
            (do (reset! num-tries 0)
                (cond (= m 'withdraw) withdraw
                      (= m 'deposit) deposit
                      :else (throw (Exception. "Unknown request"))))
            bad-password))
    ]
    dispatch))

(comment
(def acc1 (make-account 100 'pass1))
(take 6 (map (acc1 'wrongpass 'withdraw) (repeat 1)))
((acc1 'pass1 'withdraw) 10)
(take 7 (map (acc1 'wrongpass 'withdraw) (repeat 1)))
((acc1 'pass1 'withdraw) 10)
(take 8 (map (acc1 'wrongpass 'withdraw) (repeat 1)))
)

; --------------------------------------------------
(defn make-account
  "Exercise 3.3"
  [bal password]
  (let
    [balance (atom bal)
     withdraw
        (fn [amount]
          (if (>= @balance amount)
            (swap! balance #(- % amount))
            "Insufficient funds"))
     deposit
        (fn [amount] (swap! balance #(+ % amount)))
     dispatch
        (fn [pwd m]
          (if (= pwd password)
            (cond (= m 'withdraw) withdraw
                  (= m 'deposit) deposit
                  :else (throw (Exception. "Unknown request")))
            (fn [& _] "Incorrect password")))]
    dispatch))

(defn make-joint
  "Exercise 3.7"
  [acc acc-pwd new-pwd]
  (fn [pwd m]
    (if (= pwd new-pwd)
      (acc acc-pwd m)
      (fn [& _] "Incorrect password"))))

(comment
(def peter-acc (make-account 100 'open-sesame))
((peter-acc 'open-sesame 'deposit) 100)
(def paul-acc
  (make-joint peter-acc 'open-sesame 'rosebud))
((paul-acc 'open-sesame 'withdraw) 50)  ; -> bad password
((paul-acc 'rosebud 'withdraw) 50)
((peter-acc 'open-sesame 'deposit) 0)
)

; --------------------------------------------------
(def a (atom 1))

(defn f
  "Exercise 3.8"
  [x]
  (let [currenta @a]
    (reset! a x)
    (* @a x currenta)))

(reset! a 1)
(+ (f 0) (f 1))
(reset! a 1)
(+ (f 1) (f 0))

; --------------------------------------------------

(defn cons_ [x y]
  (transient [x y]))

(defn car [xy] (get xy 0))

(defn cdr [xy] (get xy 1))

(defn accumulate [op init coll]
  (if (or (nil? coll) (= coll '()))
      init
      (op (car coll)
          (accumulate op init (cdr coll)))))

(defn to-list [z]
  (accumulate (fn [x xs] (conj xs x)) '() z))

(= (to-list (cons_ 1 (cons_ 2 (cons_ 100 nil))))
   '(1 2 100))

(defn set-car! [z value] (assoc! z 0 value))
(defn set-cdr! [z value] (assoc! z 1 value))

(defn make-queue
  "Exercise 3.22"
  []
  (let
    [ queue (cons_ nil nil)
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
            [ new-pair (cons_ item nil) ]
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
                :else (throw (Exception. "Unknown request")))
          )
     ]
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
((q1 'empty?))
)

; --------------------------------------------------

(defn make-deque
  "Exercise 3.23"
  []
  (cons_ nil nil))

(defn front-ptr [deq] (car deq))
(defn rear-ptr [deq] (cdr deq))
(defn empty-deque? [deq]
  (or (nil? (front-ptr deq)) (nil? (rear-ptr deq))))
(defn set-front-ptr! [deque item] (set-car! deque item))
(defn set-rear-ptr! [deque item] (set-cdr! deque item))

(defn make-deque-link
  "deque items are 3-tuples (value, forward-ptr, backward-ptr)"
  [v f b]
  (cons_ v (cons_ f (cons_ b nil))))

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
  (cons_ (get-forward-ptr (front-ptr deq))
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

; --------------------------------------------------
; --------------------------------------------------
; --------------------------------------------------
; --------------------------------------------------
; --------------------------------------------------
