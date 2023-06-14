(ns algos.advent-of-code-2020.puzzle02
  (:require
   [clojure.string :as str]
   [clojure.java.io :as io]))

(def sample-input "1-3 a: abcde
1-3 b: cdefg
2-9 c: ccccccccc")

(defn parse-long [^String l]
  (Long/parseLong l))

(defn parse-line [^String s]
  (let [[_ min max char pwd] (re-find #"(\d+)-(\d+) (.): (.*)" s)]
    [(parse-long min) (parse-long max) (first char) pwd]))

#_(defn entry-ok? [[min max char pwd]]
  (<= min (get (frequencies pwd) char 0) max))

(defn entry-ok? [[pos1 pos2 char pwd]]
  (let [ok1 (= char (nth pwd (dec pos1)))
        ok2 (= char (nth pwd (dec pos2)))]
    (not= ok1 ok2)))


(def entry
  (-> sample-input
      (str/split #"\n")
      (nth 2)
      parse-line))

(def input
  (map parse-line (line-seq (io/reader (io/resource "puzzle_input_02.txt")))))

(def answer (count (filter entry-ok? input)))


answer

; Timing
(time (count (filter entry-ok? input)))
