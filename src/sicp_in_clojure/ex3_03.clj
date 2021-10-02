(ns sicp-in-clojure.ex3-03)

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

