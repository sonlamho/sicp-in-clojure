(ns sicp-in-clojure.ex3)

(defn make-account
  "Exercise 3.4"
  [bal password]
  (let
   [balance (atom bal)
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
        bad-password))]

    dispatch))

(comment
  (def acc1 (make-account 100 'pass1))
  (take 6 (map (acc1 'wrongpass 'withdraw) (repeat 1)))
  ((acc1 'pass1 'withdraw) 10)
  (take 7 (map (acc1 'wrongpass 'withdraw) (repeat 1)))
  ((acc1 'pass1 'withdraw) 10)
  (take 8 (map (acc1 'wrongpass 'withdraw) (repeat 1))))

