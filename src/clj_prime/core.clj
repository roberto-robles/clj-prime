(ns clj-prime.core
  (require [clojure.string :as string])
  (:gen-class))

(defn prime?
  "Returns truthy value if number is prime"
  [known-primes n]
  {:pre [(and (seq known-primes) (integer? n))]}
  (not (some #(= 0 (mod n %)) known-primes)))

;; Slow, but doesn't raise Exceptions when we want a really long list of primes > 1500
(defn- build-prime-vector
  "Generates a vector of prime numbers with length 'size'"
  [size]
  {:pre [(integer? size)] }
  (loop [prime-vec [2]
         next-to-check 3]
    (if (= size (count prime-vec))
      prime-vec
      (if (prime? prime-vec next-to-check)
        (recur (conj prime-vec next-to-check) (inc next-to-check))
        (recur prime-vec (inc next-to-check))))))

(defn- build-prime-lazy-seq
  "More efficient way of generating a list of prime numbers but not useful when
   the lazy-seq that will be generated is larger than 1500 elements due to 
   StackOverflowError safe to use when with (take 1500 ...)
   This function spects that the initial seq starts with 2"
  [s]
  (cons (first s)
        (lazy-seq (build-prime-lazy-seq (remove #(= 0 (mod % (first s))) (rest s))))))

(defn prime-seq
  "Returns a seq of prime numbers default size 10,
   if size is specified and is greater than 1500 we use the vector
   approach otherwise we use the lazy-seq approach"
  ([]
   (prime-seq 10))
  ([size]
   {:pre [(integer? size)]}
   (if (> size 1500)
     (build-prime-vector size)
     (take size (build-prime-lazy-seq (iterate inc 2))))))

(defn print-mult-table
  "Prints the multiplication table of numbers in a vector"
  [prime-vec]
  {:pre [(seq prime-vec)]}
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
  (let [primes (if (and (seq args)
                           (re-matches #"^\d+$" (first args)))
                 (prime-seq (read-string (first args)))
                 (prime-seq))]
    (print-mult-table primes)))
