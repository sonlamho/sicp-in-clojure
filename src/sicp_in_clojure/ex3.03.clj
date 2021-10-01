(ns sicp-in-clojure.ex3)

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

