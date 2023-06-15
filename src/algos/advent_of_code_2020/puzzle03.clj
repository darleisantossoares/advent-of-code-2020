(ns algos.advent-of-code-2020.puzzle03
  (:require
   [clojure.string :as str]
   [clojure.java.io :as io]))

(def sample-input "..##.......
#...#...#..
.#....#..#.
..#.#...#.#
.#...##..#.
..#.##.....
.#.#.#....#
.#........#
#.##...#...
#...##....#
.#..#...#.#")

(def input (slurp (io/resource "puzzle_input_03.txt")))

(defn input->map
  [input]
  (mapv (fn [row]
          (mapv {\# true \. false} row)) (str/split input #"\n")))

(def my-map
  (mapv (fn [row]
          (mapv {\# true \. false} row))
        (str/split sample-input #"\n")))

(defn tree?
  [m x y]
  (let [width (count (first m))]
    (get-in m [y (mod x width)])))

(def pos [my-map 0 0 0])

(defn sled [[my-map x y trees]]
  (let [x (+ x 3)
        y (+ y 1)
        tree? (tree? my-map x y)]
    (cond
      (nil? tree?)
      (reduced trees)
      (true? tree?)
      [my-map x y (inc trees)]
      :else
      [my-map x y trees])))

(defn sled-down
  [input]
  @(first
    (drop-while
     (complement reduced?)
     (iterate sled [(input->map input) 0 0 0]))))

;(sled-down sample-input)
;(sled-down (slurp (io/resource "puzzle_input_03.txt")))

(def slopes
  [[1 1]
   [3 1]
   [5 1]
   [7 1]
   [1 2]])

(defn sled2 [[down-x down-y] [my-map x y trees]]
  (let [x (+ x down-x)
        y (+ y down-y)
        tree? (tree? my-map x y)]
    (cond
      (nil? tree?)
      (reduced trees)
      (true? tree?)
      [my-map x y (inc trees)]
      :else
      [my-map x y trees])))

(defn sled-down2
  [slope input]
  @(first
    (drop-while
     (complement reduced?)
     (iterate (partial sled2 slope) [(input->map input) 0 0 0]))))

;(sled-down2 [3 1] sample-input)

(apply * (for [s slopes]
           (sled-down2 s input)))
