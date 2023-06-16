(ns clojure-ray.ray
  (:require [clojure-ray.vector :refer :all]))

(defn color [r g b]
  [r g b])

(defn sphere [centre radius color]
  {:centre centre :radius radius :color color})

(def model [
            (sphere (vec3 1 2 20) 10 (color 0.8 0.2 0.1))
            (sphere (vec3 1 20 3) 5 (color 0.1 0.2 0.9))
            (sphere (vec3 20 2 3) 20 (color 0.5 0.9 0.1))
            ])

(defn hit-sphere [center radius ray-origin ray-direction]
  (let [
        oc (vec3-minus ray-origin center)
        a (vec3-dot-product ray-direction ray-direction)
        b (* 2 (vec3-dot-product oc ray-direction))
        c (- (vec3-dot-product oc oc) (* radius radius))
        discriminant (- (* b b) (* 4 a c))]
    (> discriminant 0)))

(defn empty-color []
  (color 0 0 0))

(defn color-add [a b]
  [(+ (get a 0) (get b 0))
   (+ (get a 1) (get b 1))
   (+ (get a 2) (get b 2))])

(defn color-div [color div]
  [(/ (get color 1) div)
   (/ (get color 1) div)
   (/ (get color 2) div)])

(defn mix-colors [colors]
  (if (empty? colors)
    (empty-color)
    (color-div (reduce color-add colors) (count colors))))


(defn hit-sphere-color-or-nil [origin direction sphere]
  (if (hit-sphere (:centre sphere) (:radius sphere) origin direction)
    (:color sphere)
    nil))

(defn ray-trace [origin direction]
  (mix-colors (filter identity (map (fn [sphere] (hit-sphere-color-or-nil origin direction sphere)) model))))

(defn to-int-color-pixel [float-val]
  (int (* 255.99 float-val)))

(defn pixel-color-to-file-line [red-component green-component blue-component]
  (str red-component " " green-component " " blue-component "\n"))

(defn pixel-to-file-line [pixel-color]
  (pixel-color-to-file-line
    (to-int-color-pixel (get pixel-color 0))
    (to-int-color-pixel (get pixel-color 1))
    (to-int-color-pixel (get pixel-color 2))))


(defn engine
  [wight height lower-left-corner origin horizontal vertical]
  (clojure.string/join (for [j (range (dec height) -1 -1)
                             i (range 0 wight)
                             :let [u (/ i wight)
                                   v (/ j height)
                                   vector-on-plane (vec3-plus
                                                     (vec3-scalar-multiply u horizontal)
                                                     (vec3-scalar-multiply v vertical))
                                   direction (vec3-plus lower-left-corner vector-on-plane)
                                   pixel-color (ray-trace origin direction)]]
                         (pixel-to-file-line pixel-color))))