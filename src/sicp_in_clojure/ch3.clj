(ns sicp-in-clojure.book)

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
