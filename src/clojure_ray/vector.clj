(ns clojure-ray.vector)


(defn vec3 [x y z]
  [x y z])

(defn vec3-squared-length [[a1 a2 a3]]
  (+ (* a1 a1) (* a2 a2) (* a3 a3)))

(defn vec3-length [vec3]
  (Math/sqrt (vec3-squared-length vec3)))

(defn vec3-product [[a1 a2 a3] [b1 b2 b3]]
  [(* a1 b1) (* a2 b2) (* a3 b3)])

(defn vec3-plus [[a1 a2 a3] [b1 b2 b3]]
  [(+ a1 b1) (+ a2 b2) (+ a3 b3)])

(defn vec3-minus [[a1 a2 a3] [b1 b2 b3]]
  [(- a1 b1) (- a2 b2) (- a3 b3)])

(defn vec3-division [[a1 a2 a3] [b1 b2 b3]]
  [(/ a1 b1) (/ a2 b2) (/ a3 b3)])

(defn vec3-scalar-multiply [scalar vec3]
  (mapv #(* scalar %) vec3))

(defn vec3-dot-product [[a1 a2 a3] [b1 b2 b3]]
  (+ (* a1 b1) (* a2 b2) (* a3 b3)))

(defn vec3-cross-product [[a1 a2 a3] [b1 b2 b3]]
  [(- (* a2 b3) (* a3 b2)),
   (- (* a3 b1) (* a1 b3))
   (- (* a1 b2) (* a2 b1))])

(defn vec3-unit-vector [vec3]
  (let
    [vec-len (vec3-length vec3)]
    (vec (for [elem vec3]
           (/ elem vec-len)))))

