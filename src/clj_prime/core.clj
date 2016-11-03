(ns clj-prime.core
  (require [clojure.string :as string])
  (:gen-class))

(defn prime?
  "Returns truthy value if number is prime"
  [known-primes n]
  {:pre [(and (vector? known-primes) (integer? n))]}
  (not (some #(= 0 (mod n %)) known-primes)))

(defn gen-prime-vec
  "Prime numbers vector generator default size 10"
  ([]
   (gen-prime-vec 10))
  ([max-size]
   {:pre [(integer? max-size)] }
   (loop [prime-vec [2]
          next-to-check 3]
     (if (= max-size (count prime-vec))
       prime-vec
       (if (prime? prime-vec next-to-check)
         (recur (conj prime-vec next-to-check) (inc next-to-check))
         (recur prime-vec (inc next-to-check)))))))

(defn print-mult-table
  "Prints the multiplication table of numbers in a vector"
  [prime-vec]
  {:pre [(vector? prime-vec)]}
  (println "|   *X*   |   " (string/join "   |   " prime-vec) "   |")
  (doseq [i prime-vec
          :let [row (map #(* i %) prime-vec)]]
    (println   "|   " i  "   |   " (string/join "   |   " row) "   |")))

(defn -main
  "Prints the multiplication table of prime numbers if
   first arg 'N' is a valid integer then we show the table with
   the first Nth prime numbers, otherwise we only show the table using
   the first 10 prime numbers."
  [& args]
  (let [prime-vec (if (and (seq args)
                           (re-matches #"^\d+$" (first args)))
                    (gen-prime-vec (read-string (first args)))
                    (gen-prime-vec))]
    (print-mult-table prime-vec)))
