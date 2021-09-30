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
