(ns mark-move.markdown
  (:require [mark-move.cursor :as c]))

(import (ai.serenade.treesitter Parser Languages Node TreeSitter))
(clojure.lang.RT/loadLibrary "java-tree-sitter")

;; To see if the JNI + Tree-sitter binding + tree-sitter shared object are working.
(defn smoke-test [& rest]
   (let [parser (doto 
                    (Parser.)
                  (.setLanguage (Languages/markdown)))
         tree (.parseString parser "# one.md\n[[two.md]]")
         root (.getRootNode tree)]
     (println (.getNodeString root))
     (.getNodeString root)))

;; Grabs a  cursor (with tree-sitter/markdown) for some string
;; You will need to use with-open on the return value here, to close the cursor when you're done.
(defn cursor-for [s] 
  (with-open
    [parser (doto (Parser.) (.setLanguage (Languages/markdown)))]
    (let [tree (.parseString parser s)
          cursor (.walk (.getRootNode tree))]
      cursor)))

;; Replace a subs with another given range and new string
(defn replace-range [range s new-str]
  (let [[start a] range
        end (min a (count s))]
    (str (subs s 0 start) new-str (subs s end))))

(defn replace-in [document path new-value]
  (with-open [cursor (cursor-for document)]
    (if-not (c/move-to cursor path)
      (throw (Exception. "Cursor path not found"))
      (let [start-end (c/get-range cursor)
            new-document (replace-range start-end document new-value)]
        new-document))))

(defn parse-to-sexp [s]
  (with-open [cursor (cursor-for s)]
    (-> cursor
        (c/to-str)
        (clojure.edn/read-string))))

(defn pprint [s]
  (let [outs (with-out-str 
          (clojure.pprint/pprint 
           (parse-to-sexp s)))]
    (println outs)
    outs))

(defn smoke-test [& rest]
  (pprint "# one.md\n[[two.md]]"))




