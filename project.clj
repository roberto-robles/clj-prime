(defproject clj-prime "0.1.0"
  :description "Clojure assessment"
  :url "https://github.com/roberto-robles/clj-prime"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot clj-prime.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
