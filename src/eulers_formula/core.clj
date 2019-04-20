(ns eulers-formula.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [clojure.math.numeric-tower :as math])
  (:gen-class))

(defn contag [x] (if (coll? x) x [x 0]))
(defn c+
  ([] [0 0])
  ([& xs] (vec (apply map + (map contag xs)))))
(defn c*
  ([] [(*) 0])
  ([x] (contag x))
  ([x y]
   (let [[a b] (contag x)
         [c d] (contag y)]
     [(- (* a c) (* b d))
      (+ (* a d) (* b c))]))
  ([x y & xs] (reduce c* (c*) (concat [x y] xs))))
(defn norm [[x y]] (+ (* x x) (* y y)))
(defn div
  ([z] (let [[x y] (contag z)]
         (vec (map #(/ % (norm [x y]))
                   [x (- y)]))))
  ([z & ws] (c* z (div (apply c* ws)))))

(defn t**n-over-n!
  ([t] (t**n-over-n! t 0))
  ([t n]
   (lazy-seq
    (cons [1 0]
          (let [n (inc n)]
            (map #(c* % t (/ n))
                 (t**n-over-n! t n)))))))


(defn setup []
  (q/frame-rate 30)
  (q/color-mode :rgb))

(def iters 60)

(defn partial-exp [t]
  (reductions c+ [0 0] (take iters (t**n-over-n! [0 t]))))
(defn draw-exp [t]
  (doall (map q/line (partial-exp t) (rest (partial-exp t)))))

(def dims [1000 1000])
(def t-per-frame 0.01 )
(def TAU (* 2 Math/PI))
(defn draw []
  (q/background 250)
  (q/color 0)
  (q/fill 0 0)
  (q/stroke-weight 0.05)
  (q/translate (map #(/ % 2) dims))
  (q/scale 1 -1)
  (q/scale 40)
  (let [t (mod (* t-per-frame (q/frame-count))
               (* 1 TAU))
        ang t]
    (draw-exp t)
    (q/with-stroke [0 0 250]
      (q/arc 0 0 2 2 0 ang :open))))

(defn -main []
  (q/defsketch eulers-formula
    :title "taylor's theorem"
    :size dims
    :setup setup
    :draw draw
    :middleware [m/pause-on-error]))
