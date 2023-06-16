(ns clojure-ray.core
  (:require
    [clojure-ray.ray :as ray]))

(defn ppm-get-header [wight height]
  (str "P3\n" wight " " height "\n255\n"))


(defn -main [& _]
  (let [wight 800
         height 400
         header (ppm-get-header wight height)
         body (ray/engine wight height [-2 -1 -1]  [0 0 0] [4 0 0] [0 2 0])]
    (spit "./out/image_1.ppm" header :append false)
    (spit "./out/image_1.ppm" body :append true)))
