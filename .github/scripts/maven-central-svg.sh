#!/bin/sh
#
# Regenerates .github/assets/maven-central.svg, the animated list of Topp Standard artifacts on Maven Central.
#
# The data is read from Maven Central's repository: the group folder lists the artifacts, and each artifact's
# maven-metadata.xml gives its latest version, number of versions and last-updated date.
# The search API (search.maven.org) is not used: its index is out of date.
#
# Usage: sh .github/scripts/maven-central-svg.sh
# Needs: curl, sed, awk, grep, sort.
#
# EXCLUDE is a case-insensitive extended regular expression; matching artifacts are left out.
# The default leaves out the Commerce module. Set EXCLUDE= (empty) to show all artifacts.
#

set -eu

GROUP_PATH="com/yelstream/topp/standard"
GROUP_ID="com.yelstream.topp.standard"
BASE="https://repo1.maven.org/maven2/$GROUP_PATH"
EXCLUDE="${EXCLUDE-commerce}"

SCRIPT_DIR=$(cd "$(dirname "$0")" && pwd)
OUT="$SCRIPT_DIR/../assets/maven-central.svg"

TMP=$(mktemp -d)
trap 'rm -rf "$TMP"' EXIT

# 1. The artifacts: one subfolder per artifact.
curl -sSf -m 60 "$BASE/" | grep -o 'href="topp-standard[^"/]*/"' | sed 's/href="//;s/\/"//' | sort -u > "$TMP/artifacts.txt"
if [ -n "$EXCLUDE" ]; then
  grep -v -i -E "$EXCLUDE" "$TMP/artifacts.txt" > "$TMP/selected.txt" || true
else
  cp "$TMP/artifacts.txt" "$TMP/selected.txt"
fi

# 2. Each artifact's latest version, number of versions and last-updated date (yyyymmdd).
: > "$TMP/rows.tsv"
while read -r a; do
  m=$(curl -sSf -m 30 "$BASE/$a/maven-metadata.xml")
  v=$(printf '%s\n' "$m" | grep -o '<release>[^<]*' | sed 's/<release>//' || true)
  [ -z "$v" ] && v=$(printf '%s\n' "$m" | grep -o '<latest>[^<]*' | sed 's/<latest>//' || true)
  n=$(printf '%s\n' "$m" | grep -c '<version>' || true)
  u=$(printf '%s\n' "$m" | grep -o '<lastUpdated>[0-9]*' | sed 's/<lastUpdated>//' | cut -c1-8 || true)
  printf '%s\t%s\t%s\t%s\n' "$a" "$v" "$n" "$u" >> "$TMP/rows.tsv"
done < "$TMP/selected.txt"

N=$(wc -l < "$TMP/rows.tsv" | tr -d ' ')
V=$(awk -F'\t' '{s+=$3} END{print s}' "$TMP/rows.tsv")
LAST=$(cut -f4 "$TMP/rows.tsv" | sort | tail -1 | sed 's/\(....\)\(..\)\(..\)/\1-\2-\3/')

# 3. Layout and timing. The rows scroll up through a window of VLINES rows, then the totals line scrolls in.
W=880; LH=21; VY=82; VLINES=12; VH=$((LH*VLINES)); H=$((VY+VH+14))
T0=1.6; TSCROLL=16; TPAUSE=0.8; TSUM=0.5; HOLD=10; EXTRA=2
CYCLE=$(awk -v a=$T0 -v s=$TSCROLL -v p=$TPAUSE -v m=$TSUM -v h=$HOLD 'BEGIN{print a+s+p+m+h}')
D1=$((N*LH)); D2=$(((N+EXTRA)*LH)); Y0=$((VY+VH+LH-6))
KT=$(awk -v c=$CYCLE -v a=$T0 -v s=$TSCROLL -v p=$TPAUSE -v m=$TSUM 'BEGIN{printf "0;%.4f;%.4f;%.4f;%.4f;1", a/c, (a+s)/c, (a+s+p)/c, (a+s+p+m)/c}')

{
cat <<EOF
<svg xmlns="http://www.w3.org/2000/svg" width="$W" height="$H" viewBox="0 0 $W $H" role="img" aria-labelledby="t">
  <title id="t">Topp Standard on Maven Central: $N artifacts under $GROUP_ID, $V versions in total, latest release $LAST</title>
  <style>
    text { font-family: ui-monospace, SFMono-Regular, Menlo, Consolas, "Liberation Mono", monospace; font-size: 13px; fill: #d0d7de; white-space: pre; }
    .head { fill: #8b949e; font-size: 11.5px; letter-spacing: 0.5px; } .dim { fill: #8b949e; } .ver { fill: #3fb950; font-weight: bold; }
    .info { fill: #79c0ff; } .ok { fill: #3fb950; font-weight: bold; } .line { opacity: 0; }
  </style>

  <!-- Master clock: one ${CYCLE}-second cycle, repeated forever. -->
  <rect width="0" height="0"><animate id="loop" attributeName="x" from="0" to="0" begin="0s;loop.end" dur="${CYCLE}s"/></rect>

  <!-- Window -->
  <rect x="0.5" y="0.5" width="$((W-1))" height="$((H-1))" rx="8" fill="#0d1117" stroke="#30363d"/>
  <path d="M0.5 28 H$W" stroke="#30363d"/>
  <circle cx="18" cy="14.5" r="5.5" fill="#ff5f57"/><circle cx="36" cy="14.5" r="5.5" fill="#febc2e"/><circle cx="54" cy="14.5" r="5.5" fill="#28c840"/>
  <text x="$((W/2))" y="18.5" text-anchor="middle" class="dim">Maven Central — $GROUP_ID</text>

  <!-- Column headings -->
  <g class="line"><set attributeName="opacity" to="0" begin="loop.begin"/><set attributeName="opacity" to="1" begin="loop.begin+0.5s"/>
    <text x="18" y="56" class="head">ARTIFACT</text><text x="430" y="56" class="head">LATEST</text><text x="540" y="56" class="head">VERSIONS</text><text x="660" y="56" class="head">UPDATED</text>
    <path d="M18 66 H$((W-18))" stroke="#30363d"/></g>

  <!-- Rows: real artifacts from Maven Central, scrolling up through the window -->
  <clipPath id="view"><rect x="0" y="$VY" width="$W" height="$VH"/></clipPath>
  <g clip-path="url(#view)">
    <g>
      <animateTransform attributeName="transform" type="translate" begin="loop.begin" dur="${CYCLE}s" values="0 0;0 0;0 -$D1;0 -$D1;0 -$D2;0 -$D2" keyTimes="$KT"/>
EOF
awk -F'\t' -v y0=$Y0 -v lh=$LH '{ y=y0+(NR-1)*lh; d=substr($4,1,4) "-" substr($4,5,2) "-" substr($4,7,2);
  printf "      <text x=\"18\" y=\"%d\">%s</text><text x=\"430\" y=\"%d\" class=\"ver\">%s</text><text x=\"540\" y=\"%d\" class=\"dim\">%3d</text><text x=\"660\" y=\"%d\" class=\"dim\">%s</text>\n", y,$1,y,$2,y,$3,y,d }' "$TMP/rows.tsv"
cat <<EOF
      <text x="18" y="$((Y0+(N+1)*LH))" class="ok">$N artifacts · $V versions published · latest release $LAST</text>
    </g>
  </g>
</svg>
EOF
} > "$TMP/out.svg"

mv "$TMP/out.svg" "$OUT"
echo "Wrote $OUT: $N artifacts, $V versions, latest release $LAST."
