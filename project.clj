(defproject eulers-formula "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main eulers-formula.core
  :profile {:uberjar {:aot :all}}
  :dependencies [[org.clojure/clojure "1.10.0-RC1"]
                 [quil "2.7.1"]
                 [org.clojure/math.numeric-tower "0.0.4"]])
