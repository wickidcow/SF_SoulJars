#!/usr/bin/env python3
"""Static guardrails for immutable SoulJars release workflow policy."""
from pathlib import Path
import unittest


WORKFLOW = Path(__file__).resolve().parents[1] / ".github/workflows/maven.yml"


class ReleaseImmutabilityContract(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.publish = WORKFLOW.read_text(encoding="utf-8").split(
            "- name: Publish immutable release", 1
        )[1]

    def test_only_explicit_dispatch_or_version_tag_can_publish(self):
        self.assertIn("github.event_name == 'workflow_dispatch' &&", self.publish)
        self.assertIn("github.ref_name == github.event.repository.default_branch", self.publish)
        self.assertIn("github.event_name == 'push' && startsWith(github.ref, 'refs/tags/v')", self.publish)
        self.assertIn('"$GITHUB_REF_NAME" != "$TAG"', self.publish)

    def test_existing_source_tag_must_match(self):
        self.assertIn("git ls-remote --tags", self.publish)
        self.assertIn('"$TAG_SOURCE" != "$GITHUB_SHA"', self.publish)
        self.assertIn('"$TAG_SOURCE" == "$GITHUB_SHA"', self.publish)

    def test_published_assets_are_read_only(self):
        self.assertIn('gh release download "$TAG"', self.publish)
        self.assertIn('test -s "$EXISTING"', self.publish)
        self.assertIn('cmp -s "$JAR" "$EXISTING"', self.publish)
        self.assertNotIn("gh release upload", self.publish)
        self.assertNotIn("--clobber", self.publish)
        self.assertIn("refusing overwrite", self.publish)


if __name__ == "__main__":
    unittest.main()
