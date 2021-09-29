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

