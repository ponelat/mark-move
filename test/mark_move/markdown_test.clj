(ns mark-move.markdown.test
  (:require [mark-move.markdown :as sut]
            [clojure.test :refer [deftest testing is]]))

(deftest parsing 
  (testing "Parsing the markdown in a sexp works" 
    [(is (= '(document) (sut/parse-to-sexp "")))
     (is (= '(document (paragraph (text))) (sut/parse-to-sexp "hi")))]))
