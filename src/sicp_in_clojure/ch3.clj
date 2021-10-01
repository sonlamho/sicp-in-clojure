(ns sicp-in-clojure.ch3)

(def balance (atom 100))

(defn withdraw [amount]
  (if (>= @balance amount)
    (swap! balance #(- % amount))
    "Insufficient funds"))

(defn make-withdraw
  [bal]
  (let [balance (atom bal)]
    (fn [amount]
      (if (>= @balance amount)
        (swap! balance #(- % amount))
        "Insufficient funds"))))

(defn make-account [bal]
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
        (fn [m]
          (cond (= m 'withdraw) withdraw
                (= m 'deposit) deposit
                :else (throw (Exception. "Unknown request"))))]
    dispatch))

(comment
(def acc1 (make-account 100))
(def acc2 (make-account 100))
((acc1 'withdraw) 20)
((acc2 'withdraw) 10)
((acc1 'withdraw) 30)
((acc2 'deposit) 100)
((acc1 'deposit) 50)
((acc1 'depo) 10000000)
)

(defn cons [x y]
  (let
    [ a (atom x)
      b (atom y)
      set-x! (fn [v] (reset! a v))
      set-y! (fn [v] (reset! b v))
      dispatch
        (fn [m]
            (cond (= m 'car) @a
                  (= m 'cdr) @b
                  (= m 'set-car!) set-x!
                  (= m 'set-cdr!) set-y!
                  :else (throw (Exception. "Undefined op on cons"))))
    ]
    dispatch))

(defn car [z] (z 'car))
(defn cdr [z] (z 'cdr))
(defn set-car! [z value] ((z 'set-car!) value) z)
(defn set-cdr! [z value] ((z 'set-cdr!) value) z)

