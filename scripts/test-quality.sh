#!/bin/bash
# Quality Assurance Test Script
# Tests all subject/difficulty combinations for question quality

set -e

echo "🚀 EduPlay Quality Assurance Test Suite"
echo "========================================"
echo ""

BASE_URL="${1:-https://eduplay-g3as.onrender.com}"
MIN_QUALITY_SCORE="${2:-95}"

echo "📊 Backend URL: $BASE_URL"
echo "📊 Minimum Quality Score: ${MIN_QUALITY_SCORE}%"
echo ""

# Subject and difficulty combinations
SUBJECTS=("MATH" "FRENCH" "ARABIC" "SCIENCE" "HISTORY" "GEOGRAPHY")
DIFFICULTIES=("SIMPLE" "MOYEN" "DIFFICILE" "EXCELLENT")
LANGUAGES=("FRENCH" "ARABIC")

TOTAL_TESTS=0
PASSED_TESTS=0
FAILED_TESTS=0

echo "📋 Starting diagnostic tests..."
echo ""

for SUBJECT in "${SUBJECTS[@]}"; do
  for DIFFICULTY in "${DIFFICULTIES[@]}"; do
    for LANGUAGE in "${LANGUAGES[@]}"; do
      TOTAL_TESTS=$((TOTAL_TESTS + 1))
      
      echo -n "Testing $SUBJECT ($DIFFICULTY) in $LANGUAGE... "
      
      RESPONSE=$(curl -s "$BASE_URL/api/game/diagnostic?subject=$SUBJECT&difficulty=$DIFFICULTY&language=$LANGUAGE")
      
      # Extract quality score (handles both % and non-% formats)
      QUALITY_SCORE=$(echo "$RESPONSE" | grep -o '"qualityScore%":[0-9]*' | cut -d':' -f2)
      
      if [ -z "$QUALITY_SCORE" ]; then
        echo "❌ FAILED (No response)"
        FAILED_TESTS=$((FAILED_TESTS + 1))
        echo "Response: $RESPONSE"
        continue
      fi
      
      if [ "$QUALITY_SCORE" -ge "$MIN_QUALITY_SCORE" ]; then
        echo "✅ PASSED (Score: ${QUALITY_SCORE}%)"
        PASSED_TESTS=$((PASSED_TESTS + 1))
      else
        echo "❌ FAILED (Score: ${QUALITY_SCORE}%, minimum: ${MIN_QUALITY_SCORE}%)"
        FAILED_TESTS=$((FAILED_TESTS + 1))
        echo "Response: $RESPONSE"
      fi
    done
  done
done

echo ""
echo "========================================"
echo "📊 Test Results Summary"
echo "========================================"
echo "Total Tests: $TOTAL_TESTS"
echo "✅ Passed: $PASSED_TESTS"
echo "❌ Failed: $FAILED_TESTS"
echo "Success Rate: $(echo "scale=1; $PASSED_TESTS * 100 / $TOTAL_TESTS" | bc)%"
echo ""

if [ "$FAILED_TESTS" -eq 0 ]; then
  echo "🎉 All tests passed!"
  exit 0
else
  echo "⚠️  Some tests failed. Review the logs above."
  exit 1
fi
