# Contributing to The Aether
## Getting started for programmers
1. Install the latest 64-bit version of OpenJDK 17. Check out [Adoptium](https://adoptium.net/) for the relevant JDK build for your OS.

2. Install the latest 64-bit version of Git, which can be found on the [Git website](https://git-scm.com/).

3. Fork The Aether repository with the [fork](https://github.com/Gilded-Games/The-Aether/fork) button in the top right of GitHub.

4. We recommend to use [IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea/) for contributions. Other IDEs may work but any issues or roadblocks will not receive support.

5. We recommend a Git client if you are new to contributing, however if you know how to use Git you can also use the [command line](https://docs.gitlab.com/ee/gitlab-basics/start-using-git.html). Some client options are:
   1. IntelliJ IDEA itself ([Tutorial](https://blog.jetbrains.com/idea/2020/10/clone-a-project-from-github/))
   2. [GitHub Desktop](https://desktop.github.com/) ([Tutorial](https://docs.github.com/en/desktop/installing-and-configuring-github-desktop/overview/getting-started-with-github-desktop))
   3. [GitKraken](https://www.gitkraken.com/) ([Tutorial](https://help.gitkraken.com/gitkraken-client/guide/))
   4. [SourceTree](https://www.sourcetreeapp.com/) ([Tutorial](https://confluence.atlassian.com/get-started-with-sourcetree))

6. Clone your fork of the repository with Git (note: if using IntelliJ IDEA for Git, this will automatically open the project as well).

7. Open the locally cloned repository with IntelliJ IDEA and set up the project workspace.

   If you're not familiar with setting up IntelliJ IDEA for use with ForgeGradle projects, cpw has created a setup video on a few of the basics of ForgeGradle [here](https://www.youtube.com/watch?v=PfmlNiHonV0).

8. Create a branch for your changes named `feat/<username>/<title>`. Read up on your relevant Git tutorial if you are unsure how to do this.

9. Run `git config core.hooksPath .githooks` in your Git client's terminal to configure git hooks for the workspace.

10. Start developing!

If you have any questions or issues, or would just like to discuss Aether development, feel free to [join us on Discord](https://discord.gg/aethermod).

## Writing commits
If you are looking to commit to your fork and don't know how, make sure to read up on your relevant Git tutorial.

Once you're ready to commit, there are some things to know about our commit styling. The Aether makes uses of conventional commits. To understand this, we recommend you read up on [Semantic Versioning](https://github.com/Gilded-Games/.github/blob/main/references/VERSIONING.md) first, and then our [Conventional Commit](https://github.com/Gilded-Games/.github/blob/main/references/COMMITS.md) standards.

It is important to use conventional commits because The Aether's workflow depends on them for code review and changelogs. Not using it makes management of contributions more difficult.

It is also important to understand semantic versioning as we recommend avoiding breaking changes, and also avoiding anything that could lead to future breaking changes being made.

## Creating pull requests
To open a pull request, go to the [pull requests page](https://github.com/Gilded-Games/The-Aether/pulls), and an indicator should show up on the page to create a PR from a recent commit to a fork.

When creating a PR:
- Make sure your code conforms to the project's documentation and code styling.
- Mark it as a draft if the changes are WIP.
- Follow the template and example given when creating the PR. 
- Follow the instructions of anything written in `[]`.
- Make sure the description section meets [Conventional Commit](https://github.com/Gilded-Games/.github/blob/main/references/COMMITS.md) standards, as we put the information into the merge commit, just keep in mind the header becomes the title of the PR.

## Code review
After your PR has been opened, we will make sure to label it with the relevant status, feat, and type labels and assign it to a developer for review. We will then review your code and test if it builds through CircleCI, as well as test the contents to verify they do as described.

If we request any changes, you will have to make them for your PR to be accepted.
