(ns com.wotbrew.idx.impl.ext
  (:require [com.wotbrew.idx.impl.protocols :as p]
            [com.wotbrew.idx.impl.map :as imap]
            [com.wotbrew.idx.impl.vector :as ivec]
            [com.wotbrew.idx.impl.set :as iset])
  (:import (clojure.lang Fn Var Keyword IPersistentMap IPersistentVector IPersistentSet)))

(extend-protocol p/Idx
  nil
  (-rewrap [coll auto] nil)
  (-get-eq [coll p] nil)
  (-get-uniq [coll p] nil)
  (-get-sort [coll p] nil)
  (-del-index [coll p kind] nil)
  (-add-index [coll p kind] (-> (p/-wrap coll false) (p/-add-index p kind)))
  (-elements [coll] nil)
  (-id-element-pairs [coll] nil)
  Object
  (-rewrap [coll auto] coll)
  (-get-eq [coll p] nil)
  (-get-uniq [coll p] nil)
  (-get-sort [coll p] nil)
  (-del-index [coll p kind] coll)
  (-add-index [coll p kind] (-> (p/-wrap coll false) (p/-add-index p kind)))
  (-elements [coll] (p/-elements (p/-wrap coll false)))
  (-id-element-pairs [coll] (p/-id-element-pairs (p/-wrap coll false))))

(extend-protocol p/Property
  Fn
  (-property [this element] (this element))
  Var
  (-property [this element] (this element))
  Keyword
  (-property [this element] (this element))
  Object
  (-property [this element] (get element this))
  nil
  (-property [this element] (get element nil)))

(extend-protocol p/Wrap
  IPersistentMap
  (-wrap [this auto] (imap/->IndexedPersistentMap this nil nil nil auto))
  IPersistentVector
  (-wrap [this auto] (ivec/->IndexedPersistentVector this nil nil nil auto))
  IPersistentSet
  (-wrap [this auto] (iset/->IndexedPersistentSet this nil nil nil auto))
  nil
  (-wrap [this auto] (p/-wrap [] auto))
  Object
  (-wrap [this auto] (p/-wrap (with-meta (vec this) (meta this)) auto)))

(extend-protocol p/Unwrap
  nil
  (-unwrap [coll] coll)
  Object
  (-unwrap [coll] coll))